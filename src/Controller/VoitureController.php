<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Voiture;
use App\Repository\VoitureRepository;



namespace App\Controller;


use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Validator\Constraints\DateTime;

use Knp\Component\Pager\PaginatorInterface;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;

use App\Entity\Contrat;
use App\Entity\Voiture;
use App\Repository\ContratRepository;
use App\Repository\VoitureRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use App\Form\ContratType;
use App\Form\VoitureType;

use App\Form\Type;






class VoitureController extends AbstractController
{
    #[Route('/voiture', name: 'app_voiture')]
    public function index(): Response
    {
        return $this->render('voiture/index.html.twig', [
            'controller_name' => 'VoitureController',
        ]);
    }


#[Route('/addcontrat', name: 'addcontrat')]
public function addContratVoiture(ManagerRegistry $doctrine,
Request $request){
    $voiture= new Voiture();
    $contrat= new Contrat();
    $voiture->addContrat($contrat);
    $form=$this->createForm(VoitureType::class,
    $voiture);
    $form->handleRequest($request);
    //Action d'ajout
    if($form->isSubmitted() && $form->isValid()){
    $em =$doctrine->getManager() ;
    $em->persist($contrat);
    $em->persist($voiture);
    $em->flush();
    return $this->redirectToRoute("app_voiture");}
    return $this->render("contrat/add.html.twig",
    [
        'voiture' => $voiture,
        'f' => $form->createView(),
    ]);
}


#[Route('/addbackcontrat', name: 'addBackcontrat')]
public function addBackContratVoiture(ManagerRegistry $doctrine,
Request $request){
    $voiture= new Voiture();
    $contrat= new Contrat();
    $voiture->addContrat($contrat);
    $form=$this->createForm(VoitureType::class,
    $voiture);
    $form->handleRequest($request);
    //Action d'ajout
    if($form->isSubmitted() && $form->isValid()){
    $em =$doctrine->getManager() ;
    $em->persist($contrat);
    $em->persist($voiture);
    $em->flush();
    return $this->redirectToRoute("afficheVoiture");}
    return $this->render("contrat/addbackContrat.html.twig",
    [
        'voiture' => $voiture,
        'f' => $form->createView(),
    ]);
}





#[Route('/suppVoituret/{id}', name: 'suppVoiture')]
public function suppV(VoitureRepository $v, $id, ManagerRegistry $M): Response
{
    //recup le classroom a supprimer
    $voiture=$v->find($id);
    //recup entity manager
    $em=$M->getManager();
    //action de suppression

    $em->remove($voiture);
    $em->flush();



    return $this->redirectToRoute('afficheVoiture');
}


#[Route('/afficheVoiture', name: 'afficheVoiture')]
public function affichevoiture(VoitureRepository $r,Request $request, PaginatorInterface $Paginator): Response
{
    $v=$r->findall();
    $pagination = $Paginator->paginate(
        $r->paginationQuery(),
        $request->query->get('page',1),
        2




    );

    return $this->render('voiture/afficheV.html.twig', [
        'pagination' => $pagination,
    ]);
} 
#[Route('/voiture/{id}/contrats', name: 'viewContrats')]
public function viewContrats(int $id): Response
{
    $voiture = $this->getDoctrine()->getRepository(Voiture::class)->find($id);
    $contrats = $voiture->getContrats();

    return $this->render('voiture/view_contrats.html.twig', [
        'voiture' => $voiture,
        'contrats' => $contrats,
    ]);
}




public function edit(Request $request, Voiture $voiture)
{
    $form = $this->createForm(VoitureType::class, $voiture);

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // retrieve updated parent entity from the form data
        $voiture = $form->getData();

        // update properties of parent entity as needed
        $voiture->setMarque('Updated Marque');

        // get array of child entities from the form data
        $contrats = $voiture->getContrats();

        // loop through each child entity and add or remove as needed
        foreach ($contrats as $contrat) {
            if ($contrat->getId() === null) {
                // this is a new child entity, add it to the parent entity
                $voiture->addContrat($contrat);
            } else if ($contrat->getVoiture() !== $voiture) {
                // this child entity has been removed from the parent entity
                $voiture->removeContrat($contrat);
            }
        }

        // save the updated parent entity to the database
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($voiture);
        $entityManager->flush();

        // redirect to the list view or any other page
        return $this->redirectToRoute('afficheVoiture');
    }

    return $this->render('contrat/addback.html.twig', [
        'form' => $form->createView(),
    ]);

}





    #[Route("addVoitureJSON/new", name: "addVoitureJSON")]
    public function addVoitureJSON(Request $req,   NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $voiture = new Voiture();
        $voiture->setMarque($req->get('marque'));
        $voiture->setTypevoiture($req->get('typevoiture'));
        $voiture->setMatricule($req->get('matricule'));
        $voiture->setModele($req->get('modele'));
        $datefabrication = \DateTime::createFromFormat('Y-m-d', $req->get('datefabrication'));
        $voiture->setDatefabrication($datefabrication);
        $voiture->setTypecarburant($req->get('typecarburant'));
        $voiture-> setKilometrage($req->get('kilometrage'));
        $voiture->setEtat($req->get('etat'));

        $em->persist($voiture);
        $em->flush();

        $jsonContent = $Normalizer->normalize($voiture, 'json', ['groups' => 'voitures']);
        return new Response(json_encode($jsonContent));
    }





    #[Route("/AllVoitures", name: "all")]
    //* Dans cette fonction, nous utilisons les services NormlizeInterface et StudentRepository, 
    //* avec la méthode d'injection de dépendances.
     public function getVoitures(VoitureRepository $repo, NormalizerInterface $normalizer)
    {
        $voitures = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets 
        //* students en  tableau associatif simple.
        $studentsNormalises = $normalizer->normalize($voitures, 'json', ['groups' => "voitures"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        $json = json_encode($studentsNormalises);                                                                                                                                                                                                                                   

        //$json = $serializer->serialize($voitures, 'json', ['groups' => "voitures"]);

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }



    #[Route('/statisccontrat', name: 'stat', methods: ['GET'])]
    public function statisreclamation(VoitureRepository $r)
    {
        //on va chercher les categories
        $rech = $r->barDep();
        $arr = $r->barArr();
        
        $bar = new barChart ();
        $bar->getData()->setArrayToDataTable(
            [['contrat', 'etat'],
             ['Essence', intVal($rech)],
             ['Diesel', intVal($arr)],
            
    
            ]
        );
    
        $bar->getOptions()->setTitle('les voiture');
        $bar->getOptions()->getHAxis()->setTitle('Nombre de voiture');
        $bar->getOptions()->getHAxis()->setMinValue(0);
        $bar->getOptions()->getVAxis()->setTitle('etat');
        $bar->getOptions()->SetWidth(800);
        $bar->getOptions()->SetHeight(400);
    
    
        return $this->render('voiture/statics.html.twig', array('bar'=> $bar )); 
    
    }


    #[Route('/fonc', name: 'fonc', methods: ['GET'])]
    public function fonc(Request $request,VoitureRepository $v, PaginatorInterface $paginator): Response
    {
        $qb = $v->createQueryBuilder('c')
        ->orderBy('c.datefabrication', 'ASC'); // default order
        // Add sorting based on the query parameters
        $sortField = $request->query->get('sortField', 'datefabrication');
        $sortDirection = $request->query->get('sortDirection', 'asc');
        $qb->orderBy("c.$sortField", $sortDirection);

        // Add a search filter based on the query parameter 'q'
        $q = $request->query->get('q');
        if ($q) {
            $qb->andWhere('c.matricule LIKE :search')
            ->setParameter('search', '%' . $q . '%');
        }

        // Paginate the results
        $pagination = $paginator->paginate(
            $qb, // query builder
            $request->query->getInt('page', 1), // current page number
            2 // maximum number of results per page
        );

        // if ($this->isGranted('ROLE_ADMIN')){
            return $this->render('voiture/afficheV.html.twig', [
                'pagination' => $pagination,
                'sortField' => $sortField,
                'sortDirection' => $sortDirection,
                
            ]);
        // }else{
        //     return $this->render('error_pages/meme.html.twig');
        // }
    }



}
